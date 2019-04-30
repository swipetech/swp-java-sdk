package io.swipetech.sdk;

import io.swipetech.commons.dtos.*;

import java.util.Arrays;

public class Test {

    private static final String ORG_ID = "ORG_ID";
    private static final String API_KEY = "API_KEY";
    private static final String SECRET = "SECRET";

    public static void main(String[] args) {

        try {

            String host = "http://localhost:8080";

            Swipe swp = new SwpBuilder()
                    .setApiKey(API_KEY)
                    .setSecret(SECRET)
                    .setHost(host)
                    .build();


            AccountDTO accDTO = swp.createAccount(
                    new CreateAccountBuilder().addTag("fornecedor").build()
            ).getData().getValue();


            String CODE = "TOKEN";
            String LIMIT = "100";
            AssetDTO assetDTO = swp.issueAsset(
                    new NewAssetDTO(CODE, LIMIT, Arrays.asList("fornecedor"))
            ).getData().getValue();

            swp.makeTransfers(
                    new TransferBatchBuilder()
                            .setMemo(new Memo(MemoType.TEXT.name(), "memo"))
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
                            .setMemo(new Memo(MemoType.TEXT.name(), "hello world!"))
                            .build()
            ).getData().getValue();

            System.out.println(result);

        } catch (ErrorDTO e) {
            e.printStackTrace();
        }

    }

}

