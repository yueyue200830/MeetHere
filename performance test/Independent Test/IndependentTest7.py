from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.getAllNews()

    @task(1)
    def getAllNews(self):
        self.client.post("/getNewsUserPage")

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
