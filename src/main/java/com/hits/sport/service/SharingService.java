package com.hits.sport.service;

import com.hits.sport.dto.sharing.SharingTokenCreateDto;
import com.hits.sport.dto.sharing.SharingTokensDto;
import com.hits.sport.exception.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Service
public interface SharingService {
    String createToken(SharingTokenCreateDto sharingTokenCreateDto);
    void useToken(String value);
    List<SharingTokensDto> getMy();
    void deleteToken(UUID id);
}
