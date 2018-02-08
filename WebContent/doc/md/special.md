- <a name="login">登陆</a>

		POST /com_sys/actions/user_login.action
		to{
			userName(String):#用户名，
			password(String):#密码MD5一级加密
		}
		#状态码为201时表示登陆成功 并返回下列信息
		return {
			userId:#人员Id
			type:#人员类型
			projectId:#所属project
		}
		#修改失败时（状态码非201）并返回下列信息

		return {
			error:#出错原因
		}
		
		401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
		422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	
- <a name="signOut">登出</a>

		POST /com_sys/actions/user_signOut.action
		to{			
		}
		#状态码为201时表示登出成功
		return {
		}
		#修改失败时（状态码非201）并返回下列信息
		return {
			error:#出错原因
		}
		
		401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
		422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
		
- <a name="getDb">下载数据库文件</a>

		GET /com_sys/actions/special_getDb.action
		#只有管理员有权限操作
		#请求返回后查看状态码并根据消息头解析返回数据
		
		#状态码为200时表示成功 并返回二进制文件信息
		#文件名记录在消息头的Content-Disposition中
		response headers
			Content-Disposition:attachment;filename="monitor.db"
			Content-Type:application/octet-stream
			
		#状态码错误非200时表示失败，返回json信息
		response headers
			Content-Type:application/json;charset=utf-8
		return {
			error:#出错原因
		}
		
		401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
		403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
		404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
		406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
		410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
		500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。

	