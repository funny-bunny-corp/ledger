package com.paymentic.domain.events;

import com.paymentic.domain.ids.ShelfId;
import java.util.UUID;

public record ShelfRegistered(ShelfId shelfId) { }
