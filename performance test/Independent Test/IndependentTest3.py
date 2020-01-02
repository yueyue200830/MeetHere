from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.checkUserNameWithId()


    @task(1)
    def checkUserNameWithId(self):
        params = {
            "user_name": "momo",
            "id": 276
        }
        self.client.get("/checkUserNameWithId", params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
