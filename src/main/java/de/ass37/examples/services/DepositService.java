package de.ass37.examples.services;

import de.ass37.examples.models.DepositModel;
import org.springframework.stereotype.Service;

@Service
public class DepositService {
    public DepositModel addToDepositByUserId(DepositModel depositModel) {
        return depositModel;
    }

    public DepositModel getDeposit() {
        DepositModel depositModel = new DepositModel();
        return depositModel;
    }
}
