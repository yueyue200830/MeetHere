from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.checkUserNameExist()


    @task(1)
    def checkUserNameExist(self):
        params = {
            "user_name": "admin"
        }
        self.client.get("/checkUserNameExist", params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
