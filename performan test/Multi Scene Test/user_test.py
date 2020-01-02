from locust import HttpLocust, TaskSet, task, between

def login(u):
    global token
    params = {
        "login" : "{\"name\":\"admin\",\"password\": \"admin\"}"
    }
    response = u.client.get("/Login", params = params)

def updateUserNameById(u):
    params = {
        "id": 276,
        "newName": "momo"
    }
    u.client.get("/updateUserNameById", params = params)

def updateUserPassById(u):
    params = {
        "id": 276,
        "updateForm": "{\"pass\":\"123456-momo\",\"originalPass\": \"123456-momo\"}"
    }
    u.client.get("/updateUserPassById", params = params)

def register(u):
    params = {
        "form": "{\"name\": \"momo2\",\"password\": \"passwd\"}"
    }
    u.client.get("/Register", params = params)

class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        register(self)

    @task(1)
    def register_login_update_test(self):
        login(self)
        updateUserNameById(self)
        updateUserPassById(self)


class WebsiteUser(HttpLocust):
    host = 'http://49.235.124.124:8081'

    task_set = UserBehavior
    wait_time = between(5, 9)
