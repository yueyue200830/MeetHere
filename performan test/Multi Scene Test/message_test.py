from locust import HttpLocust, TaskSet, task, between

def login(u):
    global token
    params = {
        "login" : "{\"name\":\"admin\",\"password\": \"admin\"}"
    }
    response = u.client.get("/Login", params = params)

def getLatestMessage(u):
    u.client.post("/getLatestMessage")

def getMyMessage(u):
    params = {
        "userId": 1,
    }
    u.client.get('/getMyMessage',params = params)

def getMoreMessage(u):
    params = {
        "lastTime": "2019-10-20 13:13:13",
        "number": 10
    }
    u.client.get('/getMoreMessage', params = params)

def getMoreMyMessage(u):
    params = {
        "lastTime": "2019-10-20 13:13:13",
        "number": 10,
        "userId": 1
    }
    u.client.get('/getMoreMyMessage', params = params)

def addMessage(u):
    params = {
        "addMessageForm": "{\"title\":\"testtitle\",\"content\":\"testcontent\"}",
        "id" : 1
    }
    u.client.get('/addMessage',params = params)

def deleteMessage(u):
    params = {
        "id": 1,
    }
    u.client.get('/deleteMessage',params = params)

def editMessage(u):
    params = {
        "editMessageForm":"{\"messageTitle\":\"testtitle\"" +
                         ",\"messageContent\":\"testcontent\"" +
                         ",\"id\":5}"
    }
    u.client.get('/editMessage',params = params)

class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        login(self)

    @task(1)
    def read_add_update_delete_test(self):
        getLatestMessage(self)
        addMessage(self)
        editMessage(self)
        deleteMessage(self)
        getMoreMessage(self)

class WebsiteUser(HttpLocust):
    host = 'http://49.235.124.124:8081'

    task_set = UserBehavior
    wait_time = between(5, 9)
