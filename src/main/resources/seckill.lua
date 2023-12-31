-- 1.参数列表
local voucherId =ARGV[1]
local userId =ARGV[2]
local orderId =ARGV[3]
-- 2.数据key
local stockKey ='seckill:stock:'..voucherId
local orderKey ='seckill:order:'..voucherId
-- 3.脚本业务
-- 3.1.判断库存是否充足 get stockKey
if(tonumber(redis.call('get',stockKey))<=0)
then
    return 1
end
-- 3.2.判断用户是否下单 SISMEMBER orderKey userId
if(redis.call('sismember',orderKey,userId)==1)
then
    return 2
end
-- 3.3.扣库存 incrby stockKey -1
redis.call('incrby',stockKey,-1)
-- 3.4.下单（保存用户）sadd orderKey userId
redis.call('sadd',orderKey,userId)
-- 3.6.发送消息到队列中， XADD stream.orders * k1 v1 k2 v2 ...
redis.call('xadd','stream.orders','*','userId',userId,'voucherId',voucherId,'id',orderId)
return 0

