package com.ingsoftware.contactmanager.domain.dtos;

import java.util.List;

public record CustomPageDto<T>(List<T> content, int pageNumber, int pageSize, long totalElements) {
}
