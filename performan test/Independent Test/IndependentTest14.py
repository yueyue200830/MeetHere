from locust import HttpLocust, TaskSet, task


class UserBehavior(TaskSet):
    token = None
    def on_start(self):
        self.getAllVenueUserPage()


    @task(5)
    def getAllVenueUserPage(self):
        self.client.post("/getVenueUserPage")


class WebsiteUser(HttpLocust):
    host = 'http://localhost:4200'

    task_set = UserBehavior
    min_wait = 5000
    max_wait = 9000
