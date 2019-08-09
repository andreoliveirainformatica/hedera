package com.hedera.hedera.config.helper;

import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class HederaHelper {

    @Value("${hedera.node.address}")
    private String nodeAddress;

    @Value("${hedera.node.id}")
    private String nodeId;

    @Value("${hedera.operator.id}")
    private String operatorId;

    @Value("${hedera.operator.key}")
    private String operatorKey;

    @Bean
    public Client createHederaClient() {
        // To connect to a network with more nodes, add additional entries to the network map
        var client = new Client(Map.of(AccountId.fromString(nodeId), nodeAddress));

        // Defaults the operator account ID and key such that all generated transactions will be paid for
        // by this account and be signed by this key
        client.setOperator(AccountId.fromString(operatorId), Ed25519PrivateKey.fromString(operatorKey));
        client.setMaxTransactionFee(1_000_000_000L);
        return client;
    }
}
