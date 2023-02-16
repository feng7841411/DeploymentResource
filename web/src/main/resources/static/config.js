export const webIp = 'localhost'
export const webHost = '19101'

// export const serverIp = '10.10.140.14'

// export const serverIp = 'localhost'
// export const serverIp = '192.168.60.128'
// export const serverIp = '10.10.100.15'
// export const serverIp = '10.10.100.17'
export const serverHost = '19201'


// 北京5号
export const serverIp = '192.168.181.5'

// 这里考虑是否设计一个API路径标识，做真实数据引接和模拟数据接口的区别
// 如果是 API路径拼接的是v1，那么全部使用模拟数据
// 这样子设计，是否前端应该能够实现切换模拟数据和真实数据？
// 另外目前后端的数据放在同一个H2库表，如果切换，需要实现清除掉模拟数据，否则会导致模拟数据和真实数据被一起查出来
// 那么后端应该也要设计有同样的参数切换设计，配置文件、重启应用
export const apiVersion = 'v1'

