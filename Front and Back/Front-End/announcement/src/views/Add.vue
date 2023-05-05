<script setup>
import { ref, onMounted} from "vue";
import { getData } from "../composable/getData";
const API_URL = `http://localhost:8080/api/announcements`;
const url = `${API_URL}`;
import { useRoute, useRouter } from 'vue-router'
const { params } = useRoute()
const router = useRouter()
defineEmits(['edit', 'add'])
const data = ref([]);
const props = defineProps({
  announcement: {type: Object}
})

const updatedAnnouncement = ref({});
onMounted(async ()=>{
  data.value = await getData();
  const allId = [];
  for(let i = 0; i < data.value.length; i++){
    allId[i] = data.value[i].id
  }
  let id = allId.reduce(function (prev, curr) {
  return curr > prev ? curr : prev;
  });
  if(props.announcement === undefined){
    updatedAnnouncement.value = {
        "id": id+1,
        "announcementTitle": "",
        "announcementDescription": "",
        "publishDate": "",
        "closeDate": "",
        "announcementDisplay": "",
        "announcementCategory": ""
    }
  }
})


const categories = ref(["ทั่วไป","ทุนการศึกษา","หางาน","ฝึกงาน"]);
const Display = {
  Y:"Y",
  N:"N"
}
const myCheckbox = ref(false);
const checkDisplay =()=>{
  if(myCheckbox.value === true){
    updatedAnnouncement.value.announcementDisplay = Display.Y;
  }else{
    updatedAnnouncement.value.announcementDisplay = Display.N;
  }
  changeDate()
}
const PublishDate = ref(new Date());
const CloseDate = ref(new Date());
const PublishTime = ref(new Date());
const CloseTime = ref(new Date());
let datePublish = ref();
let dateClose = ref();
const changeDate = () => {
  const date = new Date(`${PublishDate.value}T${PublishTime.value}:00`).toISOString();
  datePublish.value = date;
  updatedAnnouncement.value.publishDate = datePublish.value;
  const date1 = new Date(`${CloseDate.value}T${CloseTime.value}:00`).toISOString();
  dateClose.value = date1;
  updatedAnnouncement.value.closeDate = dateClose.value;
};


const addNewAnnounce = async (newAnnounce) => {
  // console.log(newQuestion)
  checkDisplay()
  console.log(updatedAnnouncement.value)
  try {
    const res = await fetch(url, {
      method: 'POST',
      headers: {
        'content-type': 'application/json'
      },
      // body: JSON.stringify({
      //   text: newQuestion.text,
      //   options: newQuestion.options
      // })
      body: JSON.stringify(newAnnounce)
    })
    if (res.status === 201) {
      const addedAnnounce = await res.json()
      data.value.push(addedAnnounce)
      router.push({ name: 'Main' })
    } else throw new Error('There is something wrong, cannot add!')
  } catch (error) {
    console.log(error)
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
        <div class=" border rounded-lg mr-4">
          <textarea class="h-8 w-full" v-model="updatedAnnouncement.announcementTitle"></textarea>
        </div>
      </div>

      <div class="mt-2">
    <h3>Category</h3>
    <div class="border rounded-lg mr-4 inline-block">
      <select v-model="updatedAnnouncement.announcementCategory" class="h-8 w-full pl-12 pr-8">
        <option v-for="(category, index) in categories" :key="index">{{ category }}</option> 
      </select>
    </div>
  </div>

      <div class="mt-8">
       <h3>Description</h3>
        <div class=" border rounded-lg mr-4">
          <textarea class="h-40 w-full" v-model="updatedAnnouncement.announcementDescription"></textarea>
        </div>
      </div>

      <div class="mt-8">
       <h3>Publish Date</h3>
        <div class=" border rounded-lg inline-block mr-4">
            <input type="date" class="h-8 w-full" placeholder="dd/mm/yyyy" v-model="PublishDate"/>
        </div>
        <div class=" border rounded-lg inline-block mr-4">
            <input type="time" class="h-8 w-full" step="60" v-model="PublishTime">
        </div>
      </div>

      
      <div class="mt-8">
       <h3>Close Date</h3>
        <div class=" border rounded-lg inline-block mr-4">
            <input type="date" class="h-8 w-full" placeholder="dd/mm/yyyy" v-model="CloseDate"/>
        </div>
        <div class="border rounded-lg inline-block mr-4">  
          <input type="time" class="h-8 w-full" step="60" v-model="CloseTime"/>   
        </div>
      </div>

      <div class="mt-8">
       <h3>Display</h3>
       <div>
  <input type="checkbox" id="myCheckbox" v-model="myCheckbox">
  <label class="ml-2">Check to show this announcement</label>
</div>
</div>



<div class="mt-2">


  <button
        class="hover:bg-green-500 font-bold py-1 px-2 rounded bg-green-300 justify-center  w-20"
        @click="addNewAnnounce(updatedAnnouncement)">
          Submit
        </button>
    
        <router-link :to="{ name: 'Main' }"
        >
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
