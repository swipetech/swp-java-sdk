package sdk.java;

import dtos.*;

import java.util.ArrayList;
import java.util.List;

public class ActionBatchBuilder {

    private String memo;
    private List<INewActionDTO> actions = new ArrayList();

    public ActionBatchBuilder addMemo(String memo) {
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
        return new NewActionBatchDTO(actions, "memo");
    }
}