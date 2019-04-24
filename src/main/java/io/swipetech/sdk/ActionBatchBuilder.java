package io.swipetech.sdk;


import io.swipetech.commons.dtos.*;

import java.util.ArrayList;
import java.util.List;

public class ActionBatchBuilder {

    private Memo memo;
    private List<INewActionDTO> actions = new ArrayList();

    public ActionBatchBuilder addMemo(Memo memo) {
        this.memo = memo;
        return this;
    }

    public ActionBatchBuilder addTransfer(String from, String to, String asset, String amount) {
        actions.add(new NewTransferDTO(from, to, amount, asset));
        return this;
    }

    public ActionBatchBuilder addCreateAccount(NewAccDTO newAccDTO) {
        actions.add(newAccDTO);
        return this;
    }

    public ActionBatchBuilder addIssueAsset(String code, String limit, List<String> tags) {
        actions.add(new NewAssetDTO(code, limit, tags));
        return this;
    }

    public NewActionBatchDTO build() {
        return new NewActionBatchDTO(actions, new Memo(MemoType.TEXT.name(), "memo"));
    }
}