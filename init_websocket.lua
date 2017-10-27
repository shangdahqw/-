-- init.lua

local a=nil
 tmr.alarm(3, 10000, tmr.ALARM_AUTO, function()
----------------------------------------------

print('Setting up WIFI...')
wifi.setmode(wifi.STATION)
wifi.sta.config('207', 'shu123456')
wifi.sta.connect()
 tmr.alarm(1, 1000, tmr.ALARM_AUTO, function()
    if wifi.sta.getip() == nil then
        print('Waiting for IP ...')
    else
        print('IP is ' .. wifi.sta.getip())
        tmr.stop(1)
    end
end)

uart.setup(0, 9600, 8, uart.PARITY_NONE, uart.STOPBITS_1, 0)

local flag=nil
local ws = websocket.createClient()
ws:on("connection", function(ws)
  print('got ws connection')
  flag=1
end)
ws:on("receive", function(_, msg, opcode)
  print('got message:', msg, opcode) -- opcode is 1 for text message, 2 for binary
  uart.write(0, msg)

end)
ws:on("close", function(_, status)
  print('connection closed', status)
  ws:close()
  ws = nil -- required to lua gc the websocket client
end)


 tmr.alarm(2, 10000, tmr.ALARM_AUTO, function()
    if flag == nil then
        ws:connect('ws://192.168.31.176:8080/websocket_rtc/websocket_rtc/aaaaabbbbb/00')
         print("startalarm")
    else       
        print("stopalarm")
        tmr.stop(2)
        
    end
end)


local sendmsg=nil
uart.on("data",5,
  function(data)
  
    sendmsg="aaaaabbbbb,00,"..data 
    print(sendmsg)
    ws:send(sendmsg)
  
 end, 0)
    





    
      
 ---------------------------------------------       
        tmr.stop(3) 
end)



