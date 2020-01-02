from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.getPhotoById()



    @task(1)
    def getPhotoById(self):
        params = {
            "id": 1
        }
        self.client.get("/GetPhoto", params = params)

class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
