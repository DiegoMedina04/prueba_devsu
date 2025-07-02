package ms.cuenta_movimiento.infrastructure.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.cuenta_movimiento.domain.ports.out.external.ClientServicePort;
import ms.cuenta_movimiento.infrastructure.external.dto.ClientInfo;
import ms.cuenta_movimiento.infrastructure.external.dto.ClientResponseExternal;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientServiceAdapter implements ClientServicePort {

    private final RestTemplate restTemplate;
    
    public final ConcurrentHashMap<Long, ClientInfo> clientCache = new ConcurrentHashMap<>();

    @Override
    public Optional<String> getClientNameById(Long clientId) {
        ClientInfo cachedClient = clientCache.get(clientId);
        if (cachedClient != null) {
            return Optional.of(cachedClient.getName());
        }

        return fetchClientViaHttp(clientId)
                .map(client -> {
                    clientCache.put(clientId, new ClientInfo(client.getName()));
                    return client.getName();
                });
    }

    private Optional<ClientResponseExternal> fetchClientViaHttp(Long clientId) {
        try {

            String url = "http://cliente_persona/clientes/" + clientId;
            ClientResponseExternal response = restTemplate.getForObject(url, ClientResponseExternal.class);

            return Optional.ofNullable(response);
            
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void addClientToCache(Long clientId, String name, String identification, Boolean status) {
        ClientInfo clientInfo = new ClientInfo(clientId, name, identification, status);
        clientCache.put(clientId, clientInfo);
    }
    
    public void updateClientCache(Long clientId, String name, String identification, Boolean status) {
        ClientInfo clientInfo = new ClientInfo(clientId, name, identification, status);
        clientCache.put(clientId, clientInfo);
    }

    public void removeFromCache(Long clientId) {
         clientCache.remove(clientId);
    }

    public boolean isInCache(Long clientId) {
        return clientCache.containsKey(clientId);
    }

    @Override
    public boolean existsById(Long clientId) {
        System.out.println("ClientServiceAdapter.existsById called for ID: " + clientId);
        
        if (clientCache.containsKey(clientId)) {
            ClientInfo clientInfo = clientCache.get(clientId);
            boolean exists = clientInfo.getStatus() != null ? clientInfo.getStatus() : true;
            System.out.println("Client found in cache. Exists: " + exists);
            return exists;
        }
        
        System.out.println("Client not in cache, fetching via HTTP...");
        boolean exists = fetchClientViaHttp(clientId).isPresent();
        return exists;
    }
}