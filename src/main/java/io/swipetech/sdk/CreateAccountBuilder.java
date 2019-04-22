package io.swipetech.sdk;

import io.swipetech.commons.dtos.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAccountBuilder {

    public List<String> tags = new ArrayList();
    public List<BalanceDTO> balances = new ArrayList();
    public HashMap<String, String> fields = new HashMap();

    public CreateAccountBuilder addTag(String tag) {
        tags.add(tag);
        return this;
    }

    public CreateAccountBuilder addStartingBalance(String assetID, String balance) {
        balances.add(new BalanceDTO(assetID, null, balance));
        return this;
    }

    public CreateAccountBuilder addFields(HashMap<String, String> newFields) {
        fields = (newFields);
        return this;
    }

    public NewAccDTO build() {
        return new NewAccDTO(balances, tags, fields);
    }
}
