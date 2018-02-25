1.  <a name='reportRecord'></a>**报告表[增](#reportRecord_add)、[删](#reportRecord_delete)、[改](#reportRecord_change)、[查](#reportRecord_search)**
	- <a name="reportRecord_add">增</a>

			POST /com_sys/actions/reportRecord_add.action
			to{
				date(String):#日期，
				pressure(Double):#压力
				temperature(Double):#温度
				averageFlow(Double):#平均流量
				totalFlow(Double):#累计流量
				totalTestTime(Integer):#测试时间
				judge1(String):#判定1
				judge2(String):#判定2
				remarks(String):#备注，
				device(String):#所属设备id
			}
			#状态码为201时表示增加成功 并返回下列信息
			return {
				reportRecordId:#项目Id
			}
			#修改失败时（状态码非201）并返回下列信息

			return {
				error:#出错原因
			}
			
			401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
			422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	- <a name="reportRecord_delete">删</a>

			POST /com_sys/actions/reportRecord_deleteByIds.action
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
	- <a name="reportRecord_change">改</a>

			POST /com_sys/actions/reportRecord_updateByIds.action
			to:{
				ids:'1+',#修改Id
				keys:'key1+key2+...'
				key1:
				key2:
				.....
			}
			
			keys∈{
				date(String):#日期，
				pressure(Double):#压力
				temperature(Double):#温度
				averageFlow(Double):#平均流量
				totalFlow(Double):#累计流量
				totalTestTime(Integer):#测试时间
				judge1(String):#判定1
				judge2(String):#判定2
				remarks(String):#备注，
				device(String):#所属设备id
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
	- <a name="reportRecord_search">查</a> 
	 
			#根据Id搜索
			POST /com_sys/actions/reportRecord_getByIds.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				ids:'1+2+3+...',#搜索记录Id
			}
			
			#根据设备
			POST /com_sys/actions/reportRecord_getByDevice.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				device:#所属设备
			}			

			#若Keys为空则表示搜索下方全部字段
			Keys∈{
				reportRecordId(String):#设备id
				date(String):#日期，
				pressure(Double):#压力
				temperature(Double):#温度
				averageFlow(Double):#平均流量
				totalFlow(Double):#累计流量
				totalTestTime(Integer):#测试时间
				judge1(String):#判定1
				judge2(String):#判定2
				remarks(String):#备注，
				device(json):{
					deviceId:#设备id,
					name:#名称
				}#所属设备
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
