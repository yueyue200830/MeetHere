from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.updatePhone()




    @task(5)
    def updatePhone(self):
        params = {
            "phone": 12345678901,
            "id": 1
        }
        self.client.get("/updatePhone", params = params)


class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
