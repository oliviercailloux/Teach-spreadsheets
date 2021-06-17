package io.github.oliviercailloux.teach_spreadsheets.online.authentication;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.models.DriveSearchParameterSet;
import com.microsoft.graph.requests.DriveSearchCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.Request;

/**
 * This class aims to test the authentication just by sending a simple request :
 * search a file named "test" in the one drive of the user. We don't care if
 * this file exist or not. After you have entered your Microsoft login details,
 * if the authentication was successful you should see on your browser :
 * "Authentication complete. You can close the browser and return to the
 * application."
 * 
 */
public class AtuhenticatorTest {

	public static void main(String[] args) {
		IAuthenticationProvider token = Authenticator.getAuthenticationProvider();

		GraphServiceClient<Request> graphClient = GraphServiceClient.builder().authenticationProvider(token)
				.buildClient();

		@SuppressWarnings({ "unused" })
		DriveSearchCollectionPage search = graphClient.me().drive()
				.search(DriveSearchParameterSet.newBuilder().withQ("test").build()).buildRequest().get();
	}

}
