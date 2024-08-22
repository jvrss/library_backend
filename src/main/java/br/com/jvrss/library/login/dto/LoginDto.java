package br.com.jvrss.library.login.dto;

import java.util.UUID;

public record LoginDto(UUID id, String name, String email) {}