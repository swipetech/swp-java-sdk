package io.swipetech.sdk;

import io.swipetech.commons.dtos.*;

import java.util.ArrayList;
import java.util.List;

public class TransferBatchBuilder {

    private Memo memo;
    private List<NewTransferDTO> transfers = new ArrayList();

    public TransferBatchBuilder setMemo(Memo memo) {
        this.memo = memo;
        return this;
    }

    public TransferBatchBuilder addTransfer(String from, String to, String asset, String amount) {
        transfers.add(new NewTransferDTO(from, to, amount, asset));
        return this;
    }

    public NewTransferBatchDTO build() {
        return new NewTransferBatchDTO(transfers, memo);
    }
}

