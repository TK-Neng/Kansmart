<script setup>
import { getData, url } from "../composable/getData";
import { ref, onBeforeMount, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useAnnounceStore } from "@/stores/announce";
const announcer = useAnnounceStore();
const router = useRouter();
const data = ref([]);
const isShow = ref(false);
const isCheck404 = ref(false);
const colseShow = ref(false);
const updatedAnnouncement = ref({});
const categories = ref(["ทั่วไป", "ทุนการศึกษา", "หางาน", "ฝึกงาน"]);
let isAnnouncementActive = computed(() => announcer.mode === "active");
const toggleAnnouncementState = () => {
  isAnnouncementActive.value = !isAnnouncementActive.value
  checkmode()
};
let filteredData = ref([]);
let filteredData1 = ref([]);


const checkmode =()=>{
  if(isAnnouncementActive.value === false){
    announcer.mode = "active"
  }else if(isAnnouncementActive.value === true){
    announcer.mode = "close"
  }
}
onBeforeMount(async () => {
  data.value = await getData();
  let Dayclose
  let Monthclose
  let Yearclose
  let Daypublish
  let Monthpublish
  let Yearpublish
  let NowDay = new Date().getDate()
  let NowMonth = new Date().getMonth()
  let NowYear = new Date().getFullYear()
  if (data.value === 404) {
    isCheck404.value = true;
  }
  for (let i = 0; i < data.value.length; i++) {

  

    if (data.value[i].publishDate === null) {
      data.value[i].publishDate = "-";
    }
    if (data.value[i].closeDate === null) {
      data.value[i].closeDate = "-";
    }
    if (data.value[i].publishDate !== '-') {
      let date = new Date(data.value[i].publishDate);
      date = date.toLocaleString("en-UK", {
        day: "numeric",
        month: "short",
        year: "numeric",
        hour: "numeric",
        minute: "numeric",
      });
      Daypublish = new Date(date);
      Daypublish = Daypublish.getDate()
      Monthpublish = new Date(date);
      Monthpublish = Monthpublish.getMonth()
      Yearpublish = new Date(date);
      Yearpublish = Yearpublish.getFullYear()
      data.value[i].publishDate = date;
    }
    if (data.value[i].closeDate !== '-') {
      let date1 = new Date(data.value[i].closeDate);
      date1 = date1.toLocaleString("en-UK", {
        day: "numeric",
        month: "short",
        year: "numeric",
        hour: "numeric",
        minute: "numeric",
      });
      Dayclose = new Date(date1);
      Dayclose = Dayclose.getDate()
      Monthclose = new Date(date1);
      Monthclose = Monthclose.getMonth()
      Yearclose = new Date(date1);
      Yearclose = Yearclose.getFullYear()
      data.value[i].closeDate = date1;
      // console.log(Dayclose);
    }
    //Active mode
    if(data.value[i].announcementDisplay === "Y" && data.value[i].publishDate === '-' && data.value[i].closeDate === '-'){
      filteredData.value.push(data.value[i]);
    }
    if(data.value[i].announcementDisplay === "Y" && data.value[i].closeDate === '-'){
      if(NowYear >= Yearpublish){
        if(NowMonth > Monthpublish ){
          // if(){
            filteredData.value.push(data.value[i]);
          // }
          // console.log("True");
          // console.log("NowMonth-"+NowMonth);
          // console.log("Monthpublish-"+Monthpublish);
        }
        if(NowMonth === Monthpublish ){
          if(NowDay >= Daypublish){
            filteredData.value.push(data.value[i]);
          }
        }
        // console.log("True");
      }
    }
    if(data.value[i].announcementDisplay === "Y" && NowDay < Dayclose && NowMonth <= Monthclose && NowYear <= Yearclose && data.value[i].publishDate !== '-'){
      if(NowYear >= Yearpublish){
        if(NowMonth > Monthpublish ){
          // if(){
            filteredData.value.push(data.value[i]);
          // }
          // console.log("True");
          // console.log("NowMonth-"+NowMonth);
          // console.log("Monthpublish-"+Monthpublish);
        }
        if(NowMonth === Monthpublish ){
          if(NowDay >= Daypublish){
            filteredData.value.push(data.value[i]);
          }}
    }}
    if(data.value[i].announcementDisplay === "Y" && NowDay < Dayclose && NowMonth <= Monthclose && NowYear <= Yearclose && data.value[i].publishDate === '-'){
      filteredData.value.push(data.value[i]);
    }


    //close mode
    if(data.value[i].announcementDisplay === "Y" && NowDay >= Dayclose && NowMonth >= Monthclose && NowYear >= Yearclose && data.value[i].closeDate !== '-'){
      filteredData1.value.push(data.value[i]);
    }

   
    
  }
 
  const checkEmpty = () => {
    if (data.value.length == 0) {
      isShow.value = false;
      colseShow.value = true;
    } else {
      isShow.value = true;
      colseShow.value = false;
    }
  };
  checkEmpty();

});
let showTimeZone = ref();
showTimeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;


const gotoDetail = (id) => {
  router.push({ name: "UserDetail", params: { id: id } });
};

</script>

<template>
  <div class="min-h-screen flex flex-col">
    <div class="text-center mt-2 text-3xl font-bold">
      <h1>SIT Announcement System (SAS)</h1>
    </div>

    <div class="mt-4 flex flex-row ml-8">
      <p class="font-bold">Date/Time shown in Timezone :</p>
      <p class="ml-1">{{ showTimeZone }}</p>

      <div class="ml-auto mr-4">
        <button
          class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-6 rounded"
          @click="toggleAnnouncementState"
        >
          {{
            isAnnouncementActive
              ? "Close Announcements"
              : "Active Announcements"
          }}
        </button>
      </div>
    </div>

    <div class="flex flex-row ml-8 items-center">
      <p class="font-bold mr-2">Choose Category:</p>
      <select
        v-model="updatedAnnouncement.announcementCategory"
        class="h-8 pl-12 pr-8 border"
      >
        <option v-for="(category, index) in categories" :key="index">
          {{ category }}
        </option>
      </select>
    </div>

    <div v-show="colseShow">
      <div class="flex flex-col items-center justify-center">
        <div class="text-3xl font-bold mt-10">No Announcement</div>
      </div>
    </div>
    <div class="mt-6" v-show="isShow" v-if="isAnnouncementActive">
      <table 
        class="table-auto overflow-hidden flex items-center justify-center border-black text-lg "
      >
        <thead class="py-6">
          <tr class="table-row border">
            <th class="px-28 py-4">No.</th>
            <th class="px-4 text-left">Title</th>
            <th class="px-16">Category</th>
         
          </tr>
          <tr
            class="table-row border"
            v-for="(item, index) of filteredData"
            :key="index"
          >
            <th class="py-5">
              {{ index + 1 }}
            </th>
            <th
              class="max-w-sm overflow-hidden text-left"
              style="word-wrap: break-word"
              @click="gotoDetail(item.id)"
            >
              {{ item.announcementTitle }}
            </th>
            <th>
              {{ item.announcementCategory }}
            </th>
            
          </tr>
        </thead>
      </table>

    </div>

    <div class="mt-6" v-show="isShow" v-else>
      <table
        class="table-auto overflow-hidden flex items-center justify-center border-black text-lg" 
      >
        <thead class="py-6">
          <tr class="table-row border">
            <th class="px-28 py-4">No.</th>
            <th class="px-4 text-left">Title</th>
            <th class="px-16">Category</th>
            <th class="px-4">Close Date</th>
         
          </tr>
          <tr
            class="table-row border "
            v-for="(item, index) of filteredData1"
            :key="index"
          >
            <th class="py-5">
              {{ index + 1 }}
            </th>
            <th
              class="max-w-sm overflow-hidden text-left"
              style="word-wrap: break-word"
              @click="gotoDetail(item.id)"
            >
              {{ item.announcementTitle }}
            </th>
            <th>
              {{ item.announcementCategory }}
            </th>
            <th >
              {{ item.closeDate }}
            </th>
           
          </tr>
        </thead>
      </table>

     
    </div>
  </div>
  

  <div v-show="isCheck404">
    <div class="popup">
      <div
        class="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-screen h-screen bg-black opacity-60"
      ></div>
    </div>
    <div class="popup">
      <div
        class="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-1/3 h-5/6 bg-white rounded-xl"
      >
        <div class="top-10">
          <p class="text-black text-center text-4xl mt-16">Error</p>
        </div>
        <div class="flex flex-col">
          <img
            class="w-1/3 m-auto mt-20"
            src="./../assets/Pic/Error.png"
            alt=""
          />
        </div>
        <div class="flex flex-col fixed bottom-10 left-1/2 -translate-x-1/2">
          <router-link :to="{ name: 'UserMain' }"
            ><button
              class="text-center font-bold bg-gray-300 but text-gray-800 m-2 p-4 mb-4 text-2xl rounded-full hover:bg-red-400 transition duration-500 ease-in-out flex-col"
              @click="closeError"
            >
              Close
            </button>
          </router-link>
        </div>
      </div>
    </div>
  </div>

  <div v-show="isCheckDelete">
    <div class="popup">
      <div
        class="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-screen h-screen bg-black opacity-60"
      ></div>
    </div>
    <div class="popup">
      <div
        class="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-1/3 h-5/6 bg-white rounded-xl"
      >
        <div class="top-10">
          <p class="text-black text-center text-2xl mt-16">
            Are you sure you want to delete this data?
          </p>
        </div>
        <div class="flex flex-col">
          <img
            class="w-1/3 m-auto mt-20"
            src="./../assets/Pic/Error.png"
            alt=""
          />
        </div>
        <div class="flex flex-row fixed bottom-10 left-1/2 -translate-x-1/2">
          <button
            class="text-center font-bold bg-gray-300 but text-gray-800 m-2 p-4 mb-4 text-2xl rounded-full hover:bg-red-400 transition duration-500 ease-in-out flex-col"
            @click="deleteNow(false)"
          >
            Yes
          </button>
          <button
            class="text-center font-bold bg-gray-300 but text-gray-800 m-2 p-4 mb-4 text-2xl rounded-full hover:bg-red-400 transition duration-500 ease-in-out flex-col"
            @click="closeCheckDelete(false)"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
