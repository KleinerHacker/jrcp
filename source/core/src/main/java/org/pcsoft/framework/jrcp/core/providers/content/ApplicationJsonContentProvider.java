package org.pcsoft.framework.jrcp.core.providers.content;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pcsoft.framework.jrcp.api.providers.ContentProviderType;

/**
 * Represent a content provider for content type application/json
 */
@ContentProviderType("application/json")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationJsonContentProvider extends JsonContentProvider {
    public static final ApplicationJsonContentProvider INSTANCE = new ApplicationJsonContentProvider();
}
