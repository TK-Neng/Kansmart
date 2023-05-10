<script setup>
import { ref, onBeforeMount } from "vue";
import { url, getDataById } from "../composable/getData";
import { useRoute, useRouter } from "vue-router";
const { params } = useRoute();
const router = useRouter();
const data = ref([]);

const updatedAnnouncement = ref({});
const PublishinDate = ref();
const CloseinDate = ref();
onBeforeMount(async () => {
  data.value = await getDataById(parseInt(params.id));
  updatedAnnouncement.value = data.value;
  if (data.value.publishDate !== null) {
    let date = new Date(updatedAnnouncement.value.publishDate);
    date = date.toLocaleString("en-UK", {
      day: "numeric",
      month: "short",
      year: "numeric",
      hour: "numeric",
      minute: "numeric",
    });
    PublishinDate.value = date;
  }
    if(data.value.closeDate !== null){
      let date1 = new Date(updatedAnnouncement.value.closeDate);
      date1 = date1.toLocaleString("en-UK", {
      day: "numeric",
      month: "short",
      year: "numeric",
      hour: "numeric",
      minute: "numeric",
    });
    CloseinDate.value = date1;
    }
  if(updatedAnnouncement.value.announcementDisplay === "Y"){
  myCheckbox.value = true;
  }else{
  myCheckbox.value = false;
  }
});

const categories = ref(["ทั่วไป", "ทุนการศึกษา", "หางาน", "ฝึกงาน"]);
const Display = {
  Y: "Y",
  N: "N",
};
const myCheckbox = ref();
const checkDisplay = () => {
  if (myCheckbox.value === true  ) {
    updatedAnnouncement.value.announcementDisplay = Display.Y;
  } else {
    updatedAnnouncement.value.announcementDisplay = Display.N;
  }
  changeDate();
};
const PublishDate = ref();
const CloseDate = ref();
const PublishTime = ref();
const CloseTime = ref();
let datePublish = ref();
let dateClose = ref();
const changeDate = () => {
  if(PublishDate.value === undefined){
    updatedAnnouncement.value.publishDate = null;
  }
  if(CloseDate.value === undefined){
    updatedAnnouncement.value.closeDate = null;
  }
  if(PublishDate.value !== undefined){
    const date = new Date(`${PublishDate.value}T${PublishTime.value}:00`).toISOString();
    datePublish.value = date;
    updatedAnnouncement.value.publishDate = datePublish.value;
  }
  if(CloseDate.value !== undefined){
    const date1 = new Date(`${CloseDate.value}T${CloseTime.value}:00`).toISOString();
    dateClose.value = date1;
    updatedAnnouncement.value.closeDate = dateClose.value;
  }
  if(PublishinDate.value !== undefined){
    let date = new Date(PublishinDate.value)
    date = date.toISOString();
    updatedAnnouncement.value.publishDate = date
  }
    if(CloseinDate.value !== undefined){
    let date1 = new Date(CloseinDate.value)
    date1 = date1.toISOString();
    updatedAnnouncement.value.closeDate = date1
    }
};


const modifyAnmounce = async (updatedAnmounce) => {
  checkDisplay()
  try {
    const res = await fetch(
      url+`/${updatedAnmounce.id}`,
      {
        method: 'PUT',
        headers: {
          'content-type': 'application/json'
        },
        body: JSON.stringify(updatedAnmounce)
      }
    )
    
    if (res.ok) {
      data.value = data.value.map((announce) => {
        if (announce.id === updatedAnmounce.id) {
            announce.announcementTitle = updatedAnmounce.announcementTitle
            announce.announcementDescription = updatedAnmounce.announcementDescription
            announce.publishDate = updatedAnmounce.publishDate
            announce.closeDate = updatedAnmounce.closeDate
            announce.announcementDisplay = updatedAnmounce.announcementDisplay
            announce.announcementCategory = updatedAnmounce.announcementCategory
        }
        return announce
      })
    } else throw new Error('There is something wrong, cannot edit!')
  } catch (error) {
    router.push({ name: 'Main' })
  }
}
</script>

<template>
  <div class="min-h-screen flex flex-col">
    <div class="ml-20 mt-8">
      <h1 class="text-3xl font-bold">Announcement Detail:</h1>

      <!-- <button @click="changeDate">Test</button> -->

      <div class="mt-8">
        <h3>Title</h3>
        <div class="border rounded-lg mr-4">
          <textarea
            class="h-8 w-full"
            v-model="updatedAnnouncement.announcementTitle"
          ></textarea>
        </div>
      </div>

      <div class="mt-2">
        <h3>Category</h3>
        <div class="border rounded-lg mr-4 inline-block">
          <select
            v-model="updatedAnnouncement.announcementCategory"
            class="h-8 w-full pl-12 pr-8"
          >
            <option v-for="(category, index) in categories" :key="index">
              {{ category }}
            </option>
          </select>
        </div>
      </div>

      <div class="mt-8">
        <h3>Description</h3>
        <div class="border rounded-lg mr-4">
          <textarea
            class="h-40 w-full"
            v-model="updatedAnnouncement.announcementDescription"
          ></textarea>
        </div>
      </div>

      <div class=" flex flex-row">
        <div class="mt-8 ">
          <h3>Publish Date</h3>
          <div class="border rounded-lg inline-block mr-4">
            <input type="date" class="h-8 w-full" v-model="PublishDate" />
          </div>
          <div class="border rounded-lg inline-block mr-4">
            <input
              type="time"
              class="h-8 w-full"
              step="60"
              v-model="PublishTime"
            />
          </div>
        </div>
        <div class="mt-8 ml-20">
            <h3>Previous Publish Date</h3>
            <div class="border rounded-lg inline-block mr-4">
                <div class="h-8 w-full">{{ PublishinDate }}</div>
            </div>
        </div>
      </div>

      <div class=" flex flex-row">
        <div class="mt-8">
        <h3>Close Date</h3>
        <div class="border rounded-lg inline-block mr-4">
          <input type="date" class="h-8 w-full" v-model="CloseDate" />
        </div>
        <div class="border rounded-lg inline-block mr-4">
          <input type="time" class="h-8 w-full" step="60" v-model="CloseTime" />
        </div>
        </div>
        <div class="mt-8 ml-20">
            <h3>Previous Close Date</h3>
            <div class="border rounded-lg inline-block mr-4">
                <div class="h-8 w-full">{{ CloseinDate }}</div>
            </div>
        </div>
      </div>

      <div class="mt-8">
        <h3>Display</h3>
        <div>
          <input type="checkbox" id="myCheckbox" v-model="myCheckbox" />
          <label class="ml-2">Check to show this announcement</label>
        </div>
      </div>

      <div class="mt-2">
        <button
          class="hover:bg-green-500 font-bold py-1 px-2 rounded bg-green-300 justify-center w-20"
          @click="modifyAnmounce(updatedAnnouncement)"
        >
          Edit
        </button>

        <router-link :to="{ name: 'Main' }">
          <button
            class="hover:bg-red-500 font-bold py-1 px-2 rounded bg-red-300 justify-center ml-3 w-20"
          >
            Cancle
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
.popup {
  z-index: 9999;
}
</style>
