<script setup>
import { getDataById } from "../composable/getData";
import { useRoute } from "vue-router";
import { ref, onMounted, onBeforeMount } from "vue";

const route = useRoute();

const id = route.query.id;
const data = ref([]);
const category = ref();
onBeforeMount(async () => {
  data.value = await getDataById(id);
  if(data.value.announcementDisplay === "N"){
    data.value.publishDate = "-"
    data.value.closeDate = "-"
    }
    else if(data.value.publishDate === null){
     data.value.publishDate = "-"
    }
    else if(data.value.closeDate === null){
     data.value.closeDate = "-"
    }


    if(data.value.publishDate !== "-" ){
      let date = new Date( data.value.publishDate);
      date = date.toLocaleString("en-GB", {day: "numeric", month: "short", year: "numeric", hour: "numeric", minute: "numeric"});
      data.value.publishDate = date;
    }
    if(data.value.closeDate !== "-"){
      let date1 = new Date( data.value.closeDate);
      date1 = date1.toLocaleString("en-GB", {day: "numeric", month: "short", year: "numeric", hour: "numeric", minute: "numeric"});
      data.value.closeDate = date1;
    }
});

</script>

<template>
  <div class="min-h-screen flex flex-col">
    <div class="ml-2 mt-2 text-3xl font-bold">
      <h1>Announcement Detail:</h1>
    </div>
    <div class="border my-5 w-11/12 mx-auto flex flex-row">
      <div class="font-bold">
        <h3 class="mt-5 ml-4">Title</h3>
        <h3 class="mt-5 ml-4">Category</h3>
        <h3 class="mt-5 ml-4">Description</h3>
        <h3 class="mt-5 ml-4">Publish Date</h3>
        <h3 class="mt-5 ml-4">Close Date</h3>
        <h3 class="mt-5 ml-4 mb-5">Display</h3>
      </div>
      <div class="ml-20">
        <p class="mt-5 ml-4">{{ data.announcementTitle }}</p>
        <p class="mt-5 ml-4">{{ data.announcementCategory }}</p>
        <p class="mt-5 ml-4">{{ data.announcementDescription }}</p>
        <p class="mt-5 ml-4">{{ data.publishDate }}</p>
        <p class="mt-5 ml-4">{{ data.closeDate }}</p>
        <p class="mt-5 ml-4 mb-5">{{ data.announcementDisplay }}</p>
      </div>
    </div>
    <div class="ml-20">
      <router-link :to="{ name: 'Main' }"
        ><button
        class="hover:bg-gray-500 font-bold py-1 px-2 rounded bg-gray-300 justify-center"
        >
          Back
        </button></router-link
      >
    </div>
  </div>
</template>

<style scoped></style>
