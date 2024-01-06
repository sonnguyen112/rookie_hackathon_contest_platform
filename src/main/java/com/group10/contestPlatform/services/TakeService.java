package com.group10.contestPlatform.services;

import com.group10.contestPlatform.dtos.auth.UserTakeDetailsResponseDTO;

public interface TakeService {

    UserTakeDetailsResponseDTO getTakeDetails(Long userId);
}
