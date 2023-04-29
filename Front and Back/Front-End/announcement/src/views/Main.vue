<script setup>
import { getData } from "../composable/getData";
import { ref, onMounted, onBeforeMount } from "vue";

const data = ref([]);
onBeforeMount(async () => {
  data.value = await getData();

  for(let i = 0; i < data.value.length; i++){
    let date = new Date( data.value[i].publishDate);
    let date1 = new Date( data.value[i].closeDate);
    date = date.toLocaleString("th-TH", {day: "numeric", month: "short", year: "numeric", hour: "numeric", minute: "numeric"});
    date1 = date1.toLocaleString("th-TH", {day: "numeric", month: "short", year: "numeric", hour: "numeric", minute: "numeric"});
    data.value[i].publishDate = date;
    data.value[i].closeDate = date1;
  }

});

const isShow = ref(false);
const colseShow = ref(false);


const checkEmpty = () => {
  if (data.value.length == 0) {
    isShow.value = false;
    colseShow.value = true;
  } else {
    isShow.value = true;
    colseShow.value = false;
  }
};



</script>

<template>
  <div class="min-h-screen flex flex-col">
    <div class="text-center mt-2 text-3xl font-bold">
      <h1>SIT Announcement System (SAS)</h1>
    </div>
    <div class="mt-4 flex flex-row ml-1">
      <p class="font-bold">Date/Time shown in Timezone :</p>
      <p class="ml-1">Asia/Bangkok</p>
      <button @click="checkEmpty" class=" ml-10  bg-gray-300 rounded text-lg font-bold hover:bg-gray-500">load</button>
    </div>
    <div v-show="colseShow">
        <div class="flex flex-col items-center justify-center">
          <div class="text-3xl font-bold mt-10">No Data</div>
        </div>
    </div>
    <div class="mt-6" v-show="isShow">
      <table
        class="table-auto overflow-hidden flex items-center justify-center border-black text-lg "
      >
        <thead class="border py-6">
          <tr class="table-row border">
            <th class="px-4">No.</th>
            <th class="px-4">Title</th>
            <th class="px-4">Category</th>
            <th class="px-20">Publish Date</th>
            <th class="px-4">Close Date</th>
            <th class="px-4">Display</th>
            <th class="px-4">Action</th>
          </tr>
          <tr v-for="(item, index) of data" :key="index">
            <th>
              {{ item.id }}
            </th>
            <th>
              {{ item.announcementTitle }}
            </th>
            <th>
              {{ item.announcementCategory }}
            </th>
            <th>
              {{ item.publishDate }}
            </th>
            <th>
              {{ item.closeDate }}
            </th>
            <th>
              {{ item.announcementDisplay }}
            </th>
            <th>
              <router-link :to="`/detail?id=${item.id}`">
                <button class="hover:bg-gray-500 font-bold py-1 px-2 rounded bg-gray-300 justify-center">
                  View
                </button></router-link
              >
            </th>
          </tr>
        </thead>
      </table>
    </div>
  </div>
</template>

<style scoped></style>
