package cn.org.citycloud.utils;


public class TokenManager {
//	private static TokenManager me = new TokenManager();
//
//	private Map<String, User> tokens;
//	private Map<String, String> userToken;
//
//	public TokenManager() {
//		tokens = new ConcurrentHashMap<String, User>();
//		userToken = new ConcurrentHashMap<String, String>();
//	}
//
//	/**
//	 * 获取单例对象
//	 * 
//	 * @return
//	 */
//	public static TokenManager getMe() {
//		return me;
//	}
//
//	/**
//	 * 验证token
//	 * 
//	 * @param token
//	 * @return
//	 */
//	public User validate(String token) {
//		return tokens.get(token);
//		
//	}
//
//	/**
//	 * 生成token值
//	 * 
//	 * @param user
//	 * @return
//	 */
//	public String generateToken(User user, String secret) {
//		JWTSigner jwtSigner = new JWTSigner(secret);
//		Map<String, Object> claims = new HashMap<String, Object>();
//		claims.put("user", user);
//		String token = jwtSigner.sign(claims);
//		userToken.put(user.getId() + "", token);
//		tokens.put(token, user);
//		return token;
//	}
}
