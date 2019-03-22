package io.swipetech.sdk;

import io.swipetech.commons.dtos.*;

import java.util.ArrayList;
import java.util.List;

public class CreateAccountBuilder {

    public List<String> tags = new ArrayList();
    public List<BalanceDTO> balances = new ArrayList();
    public String fields = new String();

    public CreateAccountBuilder addTag(String tag) {
        tags.add(tag);
        return this;
    }

    public CreateAccountBuilder addStartingBalance(String assetID, String balance) {
        balances.add(new BalanceDTO(assetID, null, balance));
        return this;
    }

    public CreateAccountBuilder addFields(String fields) {
        fields = (fields);
        return this;
    }

    public NewAccDTO build() {
        return new NewAccDTO(balances, tags, fields);
    }
}