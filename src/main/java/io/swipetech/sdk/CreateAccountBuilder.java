package io.swipetech.sdk;

import commons.dtos.BalanceDTO;
import commons.dtos.NewAccDTO;

import java.util.ArrayList;
import java.util.List;

public class CreateAccountBuilder {

    public List<String> tags = new ArrayList();
    public List<BalanceDTO> balances = new ArrayList();

    public CreateAccountBuilder addTag(String tag) {
        tags.add(tag);
        return this;
    }

    public CreateAccountBuilder addStartingBalance(String assetID, String balance) {
        balances.add(new BalanceDTO(assetID, null, balance));
        return this;
    }

    public NewAccDTO build() {
        return new NewAccDTO(balances, tags);
    }
}