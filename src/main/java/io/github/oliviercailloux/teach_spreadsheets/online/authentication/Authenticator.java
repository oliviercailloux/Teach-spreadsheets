package io.github.oliviercailloux.teach_spreadsheets.online.authentication;

import java.util.List;

import com.azure.identity.InteractiveBrowserCredential;
import com.azure.identity.InteractiveBrowserCredentialBuilder;
import com.google.common.collect.ImmutableList;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;

/**
 * This class allow to get the access token from Azure active directory's
 * application in order to send requests to Microsoft Graph API.
 * 
 */
public class Authenticator {

	private final static String CLIENT_ID = "afd352ef-e48a-4244-9340-95bfc83ef33c";
	private final static List<String> SCOPES = ImmutableList.of("User.Read", "Files.ReadWrite");

	@SuppressWarnings({ "null", "unused" })
	public static IAuthenticationProvider getAuthenticationProvider() {
		if (CLIENT_ID == null) {
			throw new IllegalStateException(
					"You must initialize then client_id before calling getAuthenticationProvider");
		}
		if (!SCOPES.contains("Files.ReadWrite")) {
			throw new IllegalStateException("You must add the scope Files.ReadWrite to access on the online files ");
		}

		InteractiveBrowserCredential interactiveBrowserCredential = new InteractiveBrowserCredentialBuilder()
				.tenantId("common").clientId(CLIENT_ID).redirectUrl("http://localhost/").build();
		TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(SCOPES,
				interactiveBrowserCredential);

		return tokenCredentialAuthProvider;

	}

}