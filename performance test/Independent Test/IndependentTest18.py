from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.getMoreMessage()


    @task(5)
    def getMoreMessage(self):
        params = {
            "lastTime": "2019-10-20 13:13:13",
            "number": 10
        }
        self.client.get('/getMoreMessage', params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
