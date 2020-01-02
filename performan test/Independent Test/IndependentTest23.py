from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.login()


    def login(self):
        global token
        params = {
            "name" : "admin",
            "password": "admin"
        }
        response = self.client.get("/Login", params = params)
        #token = response.json()['token']


class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
