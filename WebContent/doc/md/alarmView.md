1.  <a name='alarmView'></a>**报警视图[增](#alarmView_add)、[删](#alarmView_delete)、[改](#alarmView_change)、[查](#alarmView_search)**
	- <a name="alarmView_add">增</a>

			#不提供增加接口
	- <a name="alarmView_delete">删</a>

			 #不提供删除接口
	- <a name="alarmView_change">改</a>

			#不提供修改接口
	- <a name="alarmView_search">查</a> 
	 
			#根据设备Id
			POST /com_sys/actions/alarmView_getByDeviceId.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				deviceId:#所属设备Id
			}			

			#若Keys为空则表示搜索下方全部字段
			Keys∈{
				deviceId(String):#设备id
				deviceName(String):#名称，
				deviceNumber(Integer):#编号
				pipeDiameter(Double):#设备管径
				deviceAddress(String):#地址，
				recordDate(String):#报告日期，
				temperatureAlarm(String):#温度报警
				deviceTtouble(String):#设备故障
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
