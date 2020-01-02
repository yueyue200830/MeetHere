from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.getMoreMyMessage()

    @task(5)
    def getMoreMyMessage(self):
        params = {
            "lastTime": "2019-10-20 13:13:13",
            "number": 10,
            "userId": 1
        }
        self.client.get('/getMoreMyMessage', params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
