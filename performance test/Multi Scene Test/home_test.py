from locust import HttpLocust, TaskSet, task, between

def login(u):
    global token
    params = {
        "login" : "{\"name\":\"admin\",\"password\": \"admin\"}"
    }
    response = u.client.get("/Login", params = params)

def getThreeNews(u):
    u.client.post("/getNewsThree")

def getVenueName(u):
    u.client.post("/getVenueName")

class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        login(self)

    @task(1)
    def read_add_update_delete_test(self):
        getThreeNews(self)
        getVenueName(self)

class WebsiteUser(HttpLocust):
    host = 'http://49.235.124.124:8081'

    task_set = UserBehavior
    wait_time = between(5, 9)
