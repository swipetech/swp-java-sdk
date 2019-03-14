package io.swipetech.sdk;

import commons.dtos.NewTransferBatchDTO;
import commons.dtos.NewTransferDTO;

import java.util.ArrayList;
import java.util.List;

public class TransferBatchBuilder {

    private String memo;
    private List<NewTransferDTO> transfers = new ArrayList();

    public TransferBatchBuilder addMemo(String memo) {
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

