package org.pcsoft.framework.jrcp.core.providers.content;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pcsoft.framework.jrcp.api.providers.ContentProviderType;

/**
 * Represent a content provider for content type text/json
 */
@ContentProviderType("text/json")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TextJsonContentProvider extends JsonContentProvider {
    public static final TextJsonContentProvider INSTANCE = new TextJsonContentProvider();
}
