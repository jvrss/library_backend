package br.com.jvrss.library.login.dto;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for Login.
 */
public record LoginDto(UUID id, String name, String email) {}