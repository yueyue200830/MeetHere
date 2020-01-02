from locust import HttpLocust, TaskSet, task, between

def login(u):
    global token
    params = {
        "login" : "{\"name\":\"admin\",\"password\": \"admin\"}"
    }
    response = u.client.get("/Login", params = params)

def getMyOrder(u):
    params1 = {
        "id": 5,
    }
    u.client.get('/getMyOrder', params = params1)
    u.client.get("/GetPhoto", params = params)

def addOrder(u):
    params = {
        "addOrderForm": "{\"revenue\":\"篮球馆\",\"phoneNumber\":\"12312312312\"," +
                    "\"date\":\"2019-12-27\",\"timeSlot\":\"2\"," +
                    "\"room\":\"4\",\"price\":\"30\"}",
        "id": 5

    }
    u.client.get('/addOrder', params = params)

def getAvailable(u):
    params = {
        "revenueName": "篮球馆",
        "date": "2019-12-27"
    }
    u.client.get('/getAvailable', params = params)

def getMyOrder(u):
    params = {
        "id": 5,
    }
    u.client.get('/getMyOrder', params = params)

def updatePhone(u):
    params = {
        "phone": 12345678901,
        "id": 1
    }
    u.client.get("/updatePhone", params = params)

def deleteOrderById(u):
    params = {
        "deleteOrderId": 2
    }
    u.client.get("/deleteOrder", params = params)

class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        login(self)

    @task(1)
    def add_get_update_delete_order_test(self):
        getAvailable(self)
        addOrder(self)
        getMyOrder(self)
        updatePhone(self)
        deleteOrderById(self)

class WebsiteUser(HttpLocust):
    host = 'http://49.235.124.124:8081'

    task_set = UserBehavior
    wait_time = between(5, 9)
