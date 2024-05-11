package com.agn.v2ray.convert;

 /*
 * Copyright 2024 Khaled AGN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class V2rayLinkToConfig {
	 

	public static String convertVMessLinkToV2RayConfig(String vmessLink) {
		try {
			// Remove "vmess://" from the link
			String base64Data = vmessLink.replace("vmess://", "");

			// Decode the Base64 URL-safe encoded string
			byte[] decodedBytes = Base64.decodeBase64(base64Data);


			try {
				JSONObject jsonData = new JSONObject(new String(decodedBytes, StandardCharsets.UTF_8));
				SERVER_ADDRESS = jsonData.optString("add", "");
				ALTER_ID = jsonData.optInt("aid", 0);
				ALPN = jsonData.optString("alpn", "");
				HEADERS_HOST = jsonData.optString("host", "");
				USER_ID = jsonData.optString("id", "");
				NET_TYPE = jsonData.optString("net", "");
				SETTINGS_PATH = jsonData.optString("path", "");
				SERVER_PORT = jsonData.optInt("port", 0);
				REMARKS = jsonData.optString("ps", "");
				SNI = jsonData.optString("sni", "");
				TLS = jsonData.optString("tls", "");
				TYPE = jsonData.optString("type", "");
				V = jsonData.optString("v", "");
				SOCKS5_PROXY_PORT = 10808;
				HTTP_PROXY_PORT = 10809;
				String host = jsonData.optString("host", "");
				if (SNI.isEmpty()) {
					SNI = host;
				}
				// Generate STREAM_SETTINGS based on NET_TYPE (tcp, ws, quic, grpc)
				STREAM_SETTINGS = "";
				if (NET_TYPE.equals("tcp")) {
					STREAM_SETTINGS = genTCP();
					SECURITY = "";

				} else if (NET_TYPE.equals("ws")) {
					STREAM_SETTINGS = genWS();
					SECURITY = "";
				} else if (NET_TYPE.equals("quic")) {
					STREAM_SETTINGS = genQUIC();
					SECURITY = "";

				} else if (NET_TYPE.equals("grpc")) {
					STREAM_SETTINGS = genGRPC();
					SECURITY = "";

				} else {
					System.out.println("Unsupported network type! Supported net types: (tcp | quic | ws | grpc).");
					System.exit(1);
				}

				// Build the configuration as a string
				StringBuilder configBuilder = new StringBuilder();
				configBuilder.append("{\n");
				configBuilder.append("  \"dns\": {\n");
				configBuilder.append("    \"hosts\": {\n");
				configBuilder.append("      \"domain:googleapis.cn\": \"googleapis.com\"\n");
				configBuilder.append("    },\n");
				configBuilder.append("    \"servers\": [\n");
				configBuilder.append("      \"1.1.1.1\"\n");
				configBuilder.append("    ]\n");
				configBuilder.append("  },\n");
				configBuilder.append("  \"inbounds\": [\n");
				configBuilder.append("    {\n");
 				configBuilder.append("      \"port\": ").append(SOCKS5_PROXY_PORT).append(",\n"); // Replace with your SOCKS5 proxy port
				configBuilder.append("      \"protocol\": \"socks\",\n");
				configBuilder.append("      \"settings\": {\n");
				configBuilder.append("        \"auth\": \"noauth\",\n");
				configBuilder.append("        \"udp\": true,\n");
				configBuilder.append("        \"userLevel\": 8\n");
				configBuilder.append("      },\n");
				configBuilder.append("      \"sniffing\": {\n");
				configBuilder.append("        \"destOverride\": [\n");
				configBuilder.append("          \"http\",\n");
				configBuilder.append("          \"tls\"\n");
				configBuilder.append("        ],\n");
				configBuilder.append("        \"enabled\": true\n");
				configBuilder.append("      },\n");
				configBuilder.append("      \"tag\": \"socks\"\n");
				configBuilder.append("    },\n");
				configBuilder.append("    {\n");
 				configBuilder.append("      \"port\": ").append(HTTP_PROXY_PORT).append(",\n"); // Replace with your HTTP proxy port
				configBuilder.append("      \"protocol\": \"http\",\n");
				configBuilder.append("      \"settings\": {\n");
				configBuilder.append("        \"userLevel\": 8\n");
				configBuilder.append("      },\n");
				configBuilder.append("      \"tag\": \"http\"\n");
				configBuilder.append("    },\n");
				configBuilder.append("    {\n");
				configBuilder.append("      \"listen\": \"127.0.0.1\",\n");
				configBuilder.append("      \"port\": 10853,\n");
				configBuilder.append("      \"protocol\": \"dokodemo-door\",\n");
				configBuilder.append("      \"settings\": {\n");
				configBuilder.append("        \"address\": \"1.1.1.1\",\n");
				configBuilder.append("        \"network\": \"tcp,udp\",\n");
				configBuilder.append("        \"port\": 53\n");
				configBuilder.append("      },\n");
				configBuilder.append("      \"tag\": \"dns-in\"\n");
				configBuilder.append("    }\n");
				configBuilder.append("  ],\n");
  				configBuilder.append("  \"log\": {\n");
				configBuilder.append("    \"loglevel\": \"warning\"\n");
				configBuilder.append("  },\n");
				configBuilder.append("  \"outbounds\": [\n");
				configBuilder.append("    {\n");
				configBuilder.append("      \"mux\": {\n");
				configBuilder.append("        \"concurrency\": 8,\n");
				configBuilder.append("        \"enabled\": false\n");
				configBuilder.append("      },\n");
				configBuilder.append("      \"protocol\": \"vmess\",\n");
				configBuilder.append("      \"settings\": {\n");
				configBuilder.append("        \"vnext\": [\n");
				configBuilder.append("          {\n");
				configBuilder.append("            \"address\": \"").append(SERVER_ADDRESS).append("\",\n");
				configBuilder.append("            \"port\": ").append(SERVER_PORT).append(",\n");
				configBuilder.append("            \"users\": [\n");
				configBuilder.append("              {\n");
				configBuilder.append("                \"alterId\": ").append(ALTER_ID).append(",\n");
				configBuilder.append("                \"encryption\": \"\",\n");
				configBuilder.append("                \"flow\": \"\",\n");
				configBuilder.append("                \"id\": \"").append(USER_ID).append("\",\n");
				configBuilder.append("                \"level\": 8,\n");
				configBuilder.append("                \"security\": \"auto\"\n");
				configBuilder.append("              }\n");
				configBuilder.append("            ]\n");
				configBuilder.append("          }\n");
				configBuilder.append("        ]\n");
				configBuilder.append("      },\n");
				configBuilder.append("      \"streamSettings\":\n");
				configBuilder.append(STREAM_SETTINGS);
				configBuilder.append("      \"tag\": \"proxy\"\n");
				configBuilder.append("    },\n");
				configBuilder.append("    {\n");
				configBuilder.append("      \"protocol\": \"freedom\",\n");
				configBuilder.append("      \"settings\": {},\n");
				configBuilder.append("      \"tag\": \"direct\"\n");
				configBuilder.append("    },\n");
				configBuilder.append("    {\n");
				configBuilder.append("      \"protocol\": \"blackhole\",\n");
				configBuilder.append("      \"settings\": {\n");
				configBuilder.append("        \"response\": {\n");
				configBuilder.append("          \"type\": \"http\"\n");
				configBuilder.append("        }\n");
				configBuilder.append("      },\n");
				configBuilder.append("      \"tag\": \"block\"\n");
				configBuilder.append("    },\n");
				configBuilder.append("    {\n");
				configBuilder.append("      \"protocol\": \"dns\",\n");
				configBuilder.append("      \"tag\": \"dns-out\"\n");
				configBuilder.append("    }\n");
				configBuilder.append("  ],\n");
				configBuilder.append("  \"routing\": {\n");
				configBuilder.append("    \"domainStrategy\": \"IPIfNonMatch\",\n");
				configBuilder.append("    \"rules\": [\n");
				configBuilder.append("      {\n");
				configBuilder.append("        \"inboundTag\": [\n");
				configBuilder.append("          \"dns-in\"\n");
				configBuilder.append("        ],\n");
				configBuilder.append("        \"outboundTag\": \"dns-out\",\n");
				configBuilder.append("        \"type\": \"field\"\n");
				configBuilder.append("      },\n");
				configBuilder.append("      {\n");
				configBuilder.append("        \"ip\": [\n");
				configBuilder.append("          \"1.1.1.1\"\n");
				configBuilder.append("        ],\n");
				configBuilder.append("        \"outboundTag\": \"proxy\",\n");
				configBuilder.append("        \"port\": \"53\",\n");
				configBuilder.append("        \"type\": \"field\"\n");
				configBuilder.append("      }\n");
				configBuilder.append("    ]\n");
				configBuilder.append("  }\n");
				configBuilder.append("}");

				// Return the string content
				return configBuilder.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private static String genTLS() {
		String tlsSettings = "";
		if (!ALPN.isEmpty()) {
			tlsSettings += "\"alpn\": [ \"" + ALPN + "\" ],";
		}

		if (!TLS.isEmpty() && TLS.equals("tls")) {
			SECURITY =  "tls";
			tlsSettings += String.format("\"tlsSettings\": {\n" +
					"        \"allowInsecure\": %s,\n" +
					"        %s\n" +
					"        \"fingerprint\": \"\",\n" +
					"        \"serverName\": \"%s\",\n" +
					"        \"show\": false\n" +
					"    },", ALLOW_INSECURE, tlsSettings,  SNI);
		}
		return tlsSettings;
	}

	private static String genWS() {
		String tlsSettings = genTLS();
		String sec;
		if (tlsSettings.isEmpty()) {
			 sec = "";
		}else {
			sec = "tls";
		}
		return String.format("{\n" +
				"    \"network\": \"ws\",\n" +
				"    \"security\": \"%s\",\n" +
				"    %s\n" +
				"    \"wsSettings\": {\n" +
				"      \"path\": \"%s\",\n" +
				"      \"headers\": {\n" +
				"        \"Host\": \"%s\"\n" +
				"      }\n" +
				"    }\n" +
				"  },\n", sec, tlsSettings, SETTINGS_PATH, HEADERS_HOST);
	}

	private static String genTCP() {
		String tlsSettings = genTLS();
 		String sec;
		if (tlsSettings.isEmpty()) {
			sec = "";
		}else {
			sec = "tls";
		}
		return String.format("{\n" +
				"    \"network\": \"tcp\",\n" +
				"    \"security\": \"%s\",\n" +
				"    %s\n" +
				"    \"tcpSettings\": {\n" +
				"     \"header\": {\n" +
				"         \"type\": \"%s\"\n" +
				"     }\n" +
				"   }\n" +
				"},\n", sec, tlsSettings, HEADER_TYPE);
	}

	private static String genQUIC() {
		String tlsSettings = genTLS();
 		String sec;
		if (tlsSettings.isEmpty()) {
			sec = "";
		}else {
			sec = "tls";
		}
		return String.format("{\n" +
				"    \"network\": \"quic\",\n" +
				"    \"security\": \"%s\",\n" +
				"    %s\n" +
				"    \"quicSettings\": {\n" +
				"      \"security\": \"none\",\n" +
				"      \"key\": \"\",\n" +
				"      \"header\": {\n" +
				"        \"type\": \"%s\"\n" +
				"      }\n" +
				"   }\n" +
				"},\n", sec, tlsSettings, HEADER_TYPE);
	}

	private static String genGRPC() {
		String tlsSettings = genTLS();
		String sec;
		if (tlsSettings.isEmpty()) {
			sec = "";
		}else {
			sec = "tls";
		}
		return String.format("{\n" +
				"    \"network\": \"grpc\",\n" +
				"    \"security\": \"%s\",\n" +
				"    %s\n" +
				"    \"grpcSettings\": {\n" +
				"      \"serviceName\": \"%s\"\n" +
				"    }\n" +
				"},\n", sec, tlsSettings, SETTINGS_PATH);
	}

	 
}
