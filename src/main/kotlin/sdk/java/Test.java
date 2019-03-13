package sdk.java;

import dtos.*;
import sdk.BackOffice;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        String host = "http://localhost:8080";

        BackOffice bo = new BackOffice("1234", false, host, false);

        NewOrgDTO newOrgDTO = new NewOrgDTO(
                "Org123",
                Network.SWIPE.toString(),
                true,
                Arrays.asList(new NewAssetDTO("Test", "100", null))
        );

        OrgDTO org = bo.createOrganization(newOrgDTO).getValue();

        sdk.Swipe swp = new SwpBuilder()
                .setApiKey(org.getApiKey())
                .setSecret(org.getSecret())
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
                        .addTransfer(org.getId(), accDTO.getId(), assetDTO.getId(), "0.1")
                        .addTransfer(org.getId(), accDTO.getId(), assetDTO.getId(), "0.1")
                        .build()
        ).getData().getValue();

        ActionBatchDTO result = swp.makeActionBatch(
                new ActionBatchBuilder()
                        .addIssueAsset("Test290", "100", null)
                        .addTransfer(org.getId(), accDTO.getId(), assetDTO.getId(), "0.1")
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

