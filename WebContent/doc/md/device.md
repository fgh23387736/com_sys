1.  <a name='device'></a>**设备[增](#device_add)、[删](#device_delete)、[改](#device_change)、[查](#device_search)**
	- <a name="device_add">增</a>

			POST /com_sys/actions/device_add.action
			to{
				deviceId(String):#设备id
				name(String):#名称，
				pipeDiameter(Double):#设备管径
				pipeDescribe(String):#管道描述
				address(String):#地址，
				number(Integer):#编号
				remarks(String):#备注
				k(Integer):#k系数
				project(Integer):#所属项目
			}
			#状态码为201时表示增加成功 并返回下列信息
			return {
				deviceId:#项目Id
			}
			#修改失败时（状态码非201）并返回下列信息

			return {
				error:#出错原因
			}
			
			401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
			422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	- <a name="device_delete">删</a>

			POST /com_sys/actions/device_deleteByIds.action
			to:{
				ids：id1+id2+id3+... #要删除的id
			}
			
			
		
			正确返回时状态码为204
			return{
			}
			
			错误时除返回码还要返回错误信息
			return{
				error:'错误信息'
			}
		
			错误码：
				401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
				403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
				404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
				406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）
				500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
	- <a name="device_change">改</a>

			POST /com_sys/actions/device_updateByIds.action
			to:{
				ids:'1+',#修改Id
				keys:'key1+key2+...'
				key1:
				key2:
				.....
			}
			
			keys∈{
				name(String):#名称，
				pipeDiameter(Double):#设备管径
				pipeDescribe(String):#管道描述
				address(String):#地址，
				number(Integer):#编号
				remarks(String):#备注
				k(Integer):#k系数
				project(Integer):#所属项目
			}
		
			
			正确返回时状态码为201
			return{

			}
			
			错误时除返回码还要返回错误信息
			return{
				error:'错误信息'
			}
		
			错误码：
				401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
				403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
				404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
				406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
				500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
	- <a name="device_search">查</a> 
	 
			#根据Id搜索
			POST /com_sys/actions/device_getByIds.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				ids:'1+2+3+...',#搜索记录Id
			}

			#根据项目和名称
			POST /com_sys/actions/device_getByProjectAndName.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				project:#所属项目，若为空则为所有项目
				Name:#用户名模糊搜索
			}			

			#若Keys为空则表示搜索下方全部字段
			Keys∈{
				deviceId(String):#设备id
				name(String):#名称，
				pipeDiameter(Double):#设备管径
				pipeDescribe(String):#管道描述
				address(String):#地址，
				k(Integer):#k系数
				number(Integer):#编号
				remarks(String):#备注
				project(json):{
					projectId:#项目id,
					name:#名称
				}#所属项目
			}

			#正确返回时状态码为200
			return{
				total：10,#未分页时搜索到总数据条数，当Page和PageSize不存在时就是resultList的长度
				resultList[
					{
						Id:,
						...
					},
					{
						Id:,
						...
					},
					...
				]

			}

			错误时除返回码还要返回错误信息
			return{
				error:'错误信息'
			}

			错误码：
				401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
				403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
				404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
				406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
				410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
				500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
