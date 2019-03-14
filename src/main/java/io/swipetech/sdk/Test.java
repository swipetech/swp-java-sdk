package io.swipetech.sdk;

import io.swipetech.commons.dtos.*;

public class Test {

    private static final String ORG_ID = "ORG_ID";
    private static final String API_KEY = "API_KEY";
    private static final String SECRET = "SECRET";

    public static void main(String[] args) {

        String host = "http://localhost:8080";

        Swipe swp = new SwpBuilder()
                .setApiKey(API_KEY)
                .setSecret(SECRET)
                .setHost(host)
                .build();

        AssetDTO assetDTO = swp.issueAsset(
                new NewAssetDTO("Test222", "100", null)
        ).getData().getValue();

        AccountDTO accDTO = swp.createAccount(
                new CreateAccountBuilder()
                        .addStartingBalance(assetDTO.getId(), "10")
                        .addTag("tag10")
                        .build()
        ).getData().getValue();

        swp.makeTransfers(
                new TransferBatchBuilder()
                        .addMemo("memo")
                        .addTransfer(ORG_ID, accDTO.getId(), assetDTO.getId(), "0.1")
                        .addTransfer(ORG_ID, accDTO.getId(), assetDTO.getId(), "0.1")
                        .build()
        ).getData().getValue();

        ActionBatchDTO result = swp.makeActionBatch(
                new ActionBatchBuilder()
                        .addIssueAsset("Test290", "100", null)
                        .addTransfer(ORG_ID, accDTO.getId(), assetDTO.getId(), "0.1")
                        .addCreateAccount(
                                new CreateAccountBuilder()
                                        .addStartingBalance(assetDTO.getId(), "10")
                                        .addTag("tag10")
                                        .build()
                        )
                        .build()
        ).getData().getValue();

        System.out.println(result);

    }

}

