//用于区分开发和生产环境，自动解决跨域问题
const isPro = Object.is(process.env.NODE_ENV, 'production')
//如果是测试环境 那么baseUrl就是‘api’前缀
//baseUrl用于axios中自动添加再所有axios的baseUrl
module.exports = {
    baseUrl: isPro ? '' : 'api/'
}