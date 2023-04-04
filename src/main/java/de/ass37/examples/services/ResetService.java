package de.ass37.examples.services;

import de.ass37.examples.models.UserModel;
import org.springframework.stereotype.Service;

@Service
public class ResetService {
    public UserModel resetDeposit() {
        return new UserModel();
    }
}
