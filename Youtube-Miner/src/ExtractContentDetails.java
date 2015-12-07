/*
 * Copyright (c) 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */



import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
//import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoContentDetailsRegionRestriction;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This sample sets and retrieves localized metadata for a video by:
 *
 * 1. Updating language of the default metadata and setting localized metadata
 *   for a video via "videos.update" method.
 * 2. Getting the localized metadata for a video in a selected language using the
 *   "videos.list" method and setting the "hl" parameter.
 * 3. Listing the localized metadata for a video using the "videos.list" method and
 *   including "localizations" in the "part" parameter.
 *
 * @author Ibrahim Ulukaya
 */
public class ExtractContentDetails {

    /**
     * Define a global instance of a YouTube object, which will be used to make
     * YouTube Data API requests.
     */
    private static YouTube youtube;


    /**
     * Set and retrieve localized metadata for a video.
     *
     * @param args command line args (not used).
     */
    
//    public static void main(String[] args) {
//    	ArrayList<String> videoIds = new ArrayList<String>();
//    	
//    	videoIds.add("kYmUJVE6Vo0");
//    	videoIds.add("6as8ahAr1Uc");
//    	videoIds.add("FyASdjZE0R0");
//    	
//    	//list(videoIds);
//    	
//    }
    
    public void list(ArrayList<String> videoIds) {
    	Main main = Main.getInstance();
    	
    	for(int i = 0; i < videoIds.size(); i++){
    		// This OAuth 2.0 access scope allows for full read/write access to the authenticated user's account.
    		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
    		try {
    			// Authorize the request.
    			Credential credential = Auth.authorize(scopes, "localizations");
    			// This object is used to make YouTube Data API requests.
    			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    	.setApplicationName("youtube-cmdline-localizations-sample").build();
    			//action string is always LIST
    			listVideoLocalizations(videoIds.get(i), main);
             
    		} catch (GoogleJsonResponseException e) {
    			System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode()
                    + " : " + e.getDetails().getMessage());
    			e.printStackTrace();
    		} catch (IOException e) {
    			System.err.println("IOException: " + e.getMessage());
    			e.printStackTrace();
    		} catch (Throwable t) {
    			System.err.println("Throwable: " + t.getMessage());
    			t.printStackTrace();
    		}
    	}
    }

    /**
     * Returns a list of localized metadata for a video.
     *
     * @param videoId The id parameter specifies the video ID for the resource
     * that is being updated.
     * @throws IOException
     */
    
    private static void listVideoLocalizations(String videoId, Main main) throws IOException {
        // Call the YouTube Data API's videos.list method to retrieve videos.
        VideoListResponse videoListResponse = youtube.videos().
            list("snippet,contentDetails").setId(videoId).execute();

        // Since the API request specified a unique video ID, the API
        // response should return exactly one video. If the response does
        // not contain a video, then the specified video ID was not found.
        List<Video> videoList = videoListResponse.getItems();
        if (videoList.isEmpty()) {
            System.out.println("Can't find a video with ID: " + videoId);
            return;
        }

        Video video = videoList.get(0);
        VideoContentDetails videoDetails = video.getContentDetails();
        VideoContentDetailsRegionRestriction regionRestrictions = videoDetails.getRegionRestriction();
        
        countRegions(regionRestrictions, main);
        
        // Print information from the API response.
        System.out.println("\n================== Video ==================\n");
        System.out.println("  - ID: " + video.getId());
        System.out.println(videoDetails.toPrettyString());
        System.out.println("\n-------------------------------------------------------------\n");
    }

    /*
     * Increment the variables storing the count for each region
     */
    public static void countRegions(VideoContentDetailsRegionRestriction regionRestrictions, Main main){
    	List<String> allowed = null;
    	List<String> blocked = null;
    	
    	//if no region restrictions
    	if(regionRestrictions == null){
    		incrementAll(main);
    	}else{
    	
    		//store allowed and blocked lists
    		if(regionRestrictions.getAllowed() != null){
    			allowed = regionRestrictions.getAllowed();
    		}
    	
    		if(regionRestrictions.getBlocked() != null){
    			blocked = regionRestrictions.getBlocked();
    		}
    	
    		if(allowed != null){
    			if(allowed.isEmpty()){
    				//if list exists and is empty, blocked in all regions
    				return;
    			}else{
    				for( String s : allowed){
    					//increment country
    					main.countryValues.addval(s);
    					
    					//increment continent
    					main.continentValues[getContinent(s)]++;
    				}
    			}
    		}else if(blocked != null){
    			if(blocked.isEmpty()){
    				incrementAll(main);
    			}else{
    				incrementAllBut(blocked, main);
    			}
    		}
    	}
    }
    
    
    /*
     * returns a number corresponding to the continent of the given country 
     */
    public static int getContinent(String country){
    	if(      country.equals("DZ") || country.equals("AO") || country.equals("SH") || country.equals("BJ") || 
    			 country.equals("BW") || country.equals("BF") || country.equals("BI") || country.equals("CM") || 
    			 country.equals("CV") || country.equals("CF") || country.equals("TD") || country.equals("KM") || 
    			 country.equals("CG") || country.equals("DJ") || country.equals("EG") || country.equals("GQ") || 
    			 country.equals("ER") || country.equals("ET") || country.equals("GA") || country.equals("GM") || 
    			 country.equals("GH") || country.equals("GW") || country.equals("GM") || country.equals("GN") || 
    			 country.equals("CI") || country.equals("KE") || country.equals("LS") || country.equals("LR") || 
    			 country.equals("LY") || country.equals("MG") || country.equals("MW") || country.equals("ML") || 
    			 country.equals("MR") || country.equals("MU") || country.equals("YT") || country.equals("MA") || 
    			 country.equals("MZ") || country.equals("NA") || country.equals("NE") || country.equals("NG") || 
    			 country.equals("ST") || country.equals("RE") || country.equals("RW") || country.equals("ST") || 
    			 country.equals("DZ") || country.equals("DZ") || country.equals("DZ") || country.equals("DZ") || 
    			 country.equals("SN") || country.equals("SC") || country.equals("SL") || country.equals("SO") || 
    			 country.equals("ZA") || country.equals("SH") || country.equals("SD") || country.equals("SZ") || 
    			 country.equals("TZ") || country.equals("TG") || country.equals("TN") || country.equals("UG") || 
    			 country.equals("CD") || country.equals("ZM") || country.equals("TZ") || country.equals("ZW") || 
    			 country.equals("SS") || country.equals("CD") || country.equals("EH")){
    		return 0;  //AFRICA
    	}else if(country.equals("AF") || country.equals("AM") || country.equals("AZ") || country.equals("BH") || 
    			 country.equals("BD") || country.equals("BT") || country.equals("BN") || country.equals("KH") || 
    			 country.equals("CN") || country.equals("CX") || country.equals("CC") || country.equals("IO") || 
    			 country.equals("GE") || country.equals("HK") || country.equals("IN") || country.equals("ID") || 
    			 country.equals("IR") || country.equals("IQ") || country.equals("IL") || country.equals("JP") || 
    			 country.equals("JO") || country.equals("KZ") || country.equals("KP") || country.equals("KR") || 
    			 country.equals("KW") || country.equals("KG") || country.equals("LA") || country.equals("LB") || 
    			 country.equals("MO") || country.equals("MY") || country.equals("MV") || country.equals("MN") || 
    			 country.equals("MM") || country.equals("NP") || country.equals("OM") || country.equals("PK") || 
    			 country.equals("PH") || country.equals("QA") || country.equals("SA") || country.equals("SG") || 
    			 country.equals("LK") || country.equals("SY") || country.equals("TW") || country.equals("TJ") || 
    			 country.equals("TH") || country.equals("TR") || country.equals("TM") || country.equals("AE") || 
    			 country.equals("UZ") || country.equals("VN") || country.equals("YE") || country.equals("PS")){
    		return 1; //ASIA
    	}else if(country.equals("AS") || country.equals("AU") || country.equals("NZ") || country.equals("CK") || 
    			 country.equals("FJ") || country.equals("PF") || country.equals("GU") || country.equals("KI") || 
    			 country.equals("MP") || country.equals("MH") || country.equals("FM") || country.equals("UM") || 
    			 country.equals("NR") || country.equals("NC") || country.equals("NZ") || country.equals("NU") || 
    			 country.equals("NF") || country.equals("PW") || country.equals("PG") || country.equals("MP") || 
    			 country.equals("SB") || country.equals("TK") || country.equals("TO") || country.equals("TV") || 
    			 country.equals("VU") || country.equals("UM") || country.equals("WF") || country.equals("WS") || 
    			 country.equals("TL") || country.equals("HM")){
    		return 2; //OCEANIA
    	}else if(country.equals("AL") || country.equals("AD") || country.equals("AT") || country.equals("BY") || 
    			 country.equals("BE") || country.equals("BA") || country.equals("BG") || country.equals("HR") || 
    			 country.equals("CY") || country.equals("CZ") || country.equals("DK") || country.equals("EE") || 
    			 country.equals("FO") || country.equals("FI") || country.equals("FR") || country.equals("DE") || 
    			 country.equals("GI") || country.equals("GR") || country.equals("HU") || country.equals("IS") || 
    			 country.equals("IE") || country.equals("IT") || country.equals("LV") || country.equals("LI") || 
    			 country.equals("LT") || country.equals("LU") || country.equals("MK") || country.equals("MT") || 
    			 country.equals("MD") || country.equals("MC") || country.equals("NL") || country.equals("NO") || 
    			 country.equals("PL") || country.equals("PT") || country.equals("RO") || country.equals("RU") || 
    			 country.equals("SM") || country.equals("RS") || country.equals("SK") || country.equals("SI") || 
    			 country.equals("ES") || country.equals("SE") || country.equals("CH") || country.equals("UA") || 
    			 country.equals("GB") || country.equals("VA") || country.equals("RS") || country.equals("IM") || 
    			 country.equals("ME") || country.equals("AX") || country.equals("GG") || country.equals("JE") ||
    			 country.equals("SJ") ){
    		return 3; //EUROPE
    	}else if(country.equals("AI") || country.equals("AG") || country.equals("AW") || country.equals("BS") || 
    			 country.equals("BB") || country.equals("BZ") || country.equals("BM") || country.equals("VG") || 
    			 country.equals("CA") || country.equals("KY") || country.equals("CR") || country.equals("CU") || 
    			 country.equals("CW") || country.equals("DM") || country.equals("DO") || country.equals("SV") || 
    			 country.equals("GL") || country.equals("GD") || country.equals("GP") || country.equals("GT") || 
    			 country.equals("HT") || country.equals("HN") || country.equals("JM") || country.equals("MQ") || 
    			 country.equals("MX") || country.equals("PM") || country.equals("MS") || country.equals("CW") || 
    			 country.equals("KN") || country.equals("NI") || country.equals("PA") || country.equals("PR") || 
    			 country.equals("KN") || country.equals("LC") || country.equals("PM") || country.equals("VC") || 
    			 country.equals("TT") || country.equals("TC") || country.equals("VI") || country.equals("US") || 
    			 country.equals("SX") || country.equals("BQ") || country.equals("SA") || country.equals("SE")){
    		return 4; //NORTH_AMERICA
    	}else if(country.equals("AR") || country.equals("BO") || country.equals("BR") || country.equals("CL") || 
    			 country.equals("CO") || country.equals("EC") || country.equals("FK") || country.equals("GF") || 
    			 country.equals("GY") || country.equals("PY") || country.equals("PE") || country.equals("SR") || 
    			 country.equals("UY") || country.equals("VE")){
    		return 5; //SOUTH_AMERICA
    	}else{
    		System.out.println(country);
    		return 6;
    	}
    }
    
    public static void incrementAll(Main main){
    	String[] countries = {"AD", "AE", "AF", "AG", "AI", "AL", "AM", "AO", "AR", "AS", "AT", "AU", "AW", "AX",
    			"AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BM", "BN", "BO", "BR", "BS", "BT", "BW", "BY",
    			"BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CU", "CV", "CX", "CY",
    			"CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "FI", "FJ", "FK", "FM", "FO", "FR",
    			"GA", "GB", "GD", "GE", "GF", "GG", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GT", "GU", "GW",
    			"GY", "HK", "HM", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IM", "IN", "IO", "IR", "IS", "IT", "JE", "JM", "JO",
    			"JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS",
    			"LT", "LU", "LV", "LY", "MA", "MC", "MD", "ME", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS",
    			"MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU",
    			"NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RS",
    			"RO", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "ST",
    			"SV", "SY", "SZ", "TC", "TD", "TG", "TH", "TJ", "TK", "TM", "TN", "TO", "TR", "TT", "TV", "TW",
    			"TZ", "UA", "UG", "UM", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "YE", "YT", 
    			"ZA", "ZM", "ZW"};
    	
    	//stores whether continent has been incremented
    	boolean[] continentIncremented = {false, false, false, false, false, false, false};
    	
    	
    	for(int i = 0; i < countries.length; i++){
    		int continent = getContinent(countries[i]);
    		
    		//increment country
    		main.countryValues.addval(countries[i]);
    		
    		//only increment continent for this video if that continent hasn't been incremented before
    		if(continentIncremented[continent] == false){
    			continentIncremented[continent] = true;
    			main.continentValues[continent]++;
    		}
    	}
    		
    }
    
    public static void incrementAllBut(List<String> blocked, Main main){
    	String[] countries = {"AD", "AE", "AF", "AG", "AI", "AL", "AM", "AO", "AR", "AS", "AT", "AU", "AW", "AX",
    			"AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BM", "BN", "BO", "BR", "BS", "BT", "BW", "BY",
    			"BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CU", "CV", "CX", "CY",
    			"CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "ER", "ES", "ET", "FI", "FJ", "FK", "FM", "FO", "FR",
    			"GA", "GB", "GD", "GE", "GF", "GG", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GT", "GU", "GW",
    			"GY", "HK", "HM", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IM", "IN", "IO", "IR", "IS", "IT", "JE", "JM", "JO",
    			"JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS",
    			"LT", "LU", "LV", "LY", "MA", "MC", "MD", "ME", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS",
    			"MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU",
    			"NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RS",
    			"RO", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "ST",
    			"SV", "SY", "SZ", "TC", "TD", "TG", "TH", "TJ", "TK", "TM", "TN", "TO", "TR", "TT", "TV", "TW",
    			"TZ", "UA", "UG", "UM", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "YE", "YT", 
    			"ZA", "ZM", "ZW"};
    	
    	boolean[] continentIncremented = {false, false, false, false, false, false, false};
    	
    	for(int i = 0; i < countries.length; i++){
    		int continent = getContinent(countries[i]);
    		
    		
    		//increment all countries as long as they aren't in blocked
    		if(!blocked.contains(countries[i])){
        		main.countryValues.addval(countries[i]);
        		
        		//only increment continent for this video if that continent hasn't been incremented before
        		if(!continentIncremented[continent]){
        			continentIncremented[continent] = true;
        			main.continentValues[continent]++;
        		}
    		}
    	}
    		
    }
    
    
    public enum Action {
      SET,
      GET,
      LIST
    }
    
    public enum Country {
    	AFRICA,
        ASIA,
        OCEANIA,
        EUROPE,
        NORTH_AMERICA,
        SOUTH_AMERICA
      }
}