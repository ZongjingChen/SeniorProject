// import {loader, clock, scene, camera, renderer, stats} from "./templateFbxLoader.js"
// import * as THREE from 'https://unpkg.com/three@0.126.1/build/three.module.js';
// let mixer;
// loader.load( '../res/models/Samba Dancing.fbx', function ( object ) {
//     // load object and add to scene
//     mixer = new THREE.AnimationMixer(object);
//     const action = mixer.clipAction(object.animations[1]);
//     action.play();
//     scene.add(object);
//     playAnimation(object);
// })
//
// function playAnimation(object) {
//         console.log(object);
//         let delta = 0;
//         let interval = 1 / 30;
//         animate();
//
//         function animate() {
//             requestAnimationFrame(animate);
//             // const delta = clock.getDelta();
//             delta += clock.getDelta();
//             if (delta > interval) {
//                 if (mixer) mixer.update(delta);
//                 let skeleton = object.children[0].children[2];
//                 let degree = 0.6;
//                 if (skeleton.rotation.x < degree) {
//                     riseLeg(degree);
//                 } else {
//                     if (object.children[0].rotation.x < 0.7) {
//                         object.children[0].rotation.x += 0.01 / 2;
//                         object.children[0].children[3].rotation.x -= 0.01 / 2;
//                         object.children[2].skeleton.bones[26].rotation.y += 0.018 / 2;
//                         object.children[2].skeleton.bones[27].rotation.y += 0.022 / 2;
//                         object.children[2].skeleton.bones[28].rotation.y += 0.006 / 2;
//                     }
//                 }
//                 renderer.render(scene, camera);
//                 stats.update();
//                 delta = delta % interval;
//
//                 function riseLeg(degree) {
//                 if (skeleton.rotation.x < degree) {
//                     skeleton.rotation.x += 0.01 / 2;
//                 }
//             }
//             }
//
//
//         }
//     }

import {human} from './templateFbxLoader.js';
console.log(human);
